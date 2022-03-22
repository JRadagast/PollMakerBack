package com.demo.PollMakerBack.repository;

import com.demo.PollMakerBack.SpringJdbcConfig;
import com.demo.PollMakerBack.bean.Usuario;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static String QUERY_SAVE = "INSERT INTO usuario (login, password) VALUES " +
            "( :LOGIN, :PASSWORD )";

    private static String QUERY_FIND_BY_ID = "SELECT * FROM usuario WHERE idusuario = :IDUSER";

    private static String QUERY_FIND_LOGGED_USED = "SELECT * FROM usuario WHERE login = :LOGIN";

    public Usuario save(Usuario user) {
        setDataSource();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = getParamsInsert( user );

        this.jdbcTemplate.update(QUERY_SAVE, params, keyHolder, new String[] { "idusuario" });
        Usuario u = null;

        try {
            u = getById(keyHolder.getKey().longValue());
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return u;
    }

    public MapSqlParameterSource getParamsInsert( Usuario user ){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("LOGIN", user.getUsername() );
        params.addValue("PASSWORD", user.getPassword() );

        return params;
    }

    public Usuario getById(Long idUser ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDUSER", idUser);

        List<Usuario> x = this.jdbcTemplate.query(QUERY_FIND_BY_ID, params, rowMapper() );

        return x != null && x.size() > 0 ? x.get(0) : null;
    }

    public Usuario getByUsername(String username ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("LOGIN", username);

        List<Usuario> x = this.jdbcTemplate.query(QUERY_FIND_LOGGED_USED, params, rowMapper() );

        return x != null && x.size() > 0 ? x.get(0) : null;
    }

    public RowMapper<Usuario> rowMapper() throws SQLException {
        return new RowMapper<Usuario>() {
            @Override
            public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
                Usuario u = new Usuario();

                u.setIdusuario( rs.getLong("IDUSUARIO") );
                u.setUsername( rs.getString("LOGIN") );
                u.setPassword( rs.getString("PASSWORD") );

                return u;
            }
        };
    }

    public void setDataSource() {
        SpringJdbcConfig config = new SpringJdbcConfig();
        this.jdbcTemplate = new NamedParameterJdbcTemplate( config.postgresDataSource() );
    }
}
