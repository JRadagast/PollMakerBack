package com.demo.PollMakerBack.repository;

import com.demo.PollMakerBack.SpringJdbcConfig;
import com.demo.PollMakerBack.bean.Poll;
import com.demo.PollMakerBack.bean.PollOption;
import com.demo.PollMakerBack.bean.Usuario;
import com.demo.PollMakerBack.service.PollOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository()
public class PollRepository {

    @Autowired
    private PollOptionService serviceOptions;

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static String QUERY_SAVE = "INSERT INTO poll (title, description, iduser, requiresauthentication) VALUES " +
            "( :TITLE, :DESCRIPTION, :IDUSER, :REQUIRESAUTHENTICATION )";

    private static String QUERY_UPDATE = "UPDATE poll SET " +
            " title = :TITLE, description = :DESCRIPTION, iduser = :IDUSER, requiresauthentication = :REQUIRESAUTHENTICATION where idpoll = :IDPOLL";

    private static String QUERY_FIND_ALL_BY_USER = "SELECT * FROM poll where iduser = :USERID";

    private static String QUERY_FIND_ALL_BY_IDS = "SELECT * FROM poll where idpoll in ( :IDPOLLS )";

    private static String QUERY_FIND_BY_ID = "SELECT * FROM poll WHERE idpoll = :IDPOLL";

    public List<Poll> getAllByUserId( Long iduser ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("USERID", iduser);

        List<Poll> x = this.jdbcTemplate. query(QUERY_FIND_ALL_BY_USER, params, rowMapperNoOptions() );

        return x;
    }

    public List<Poll> getAllWithIds( List<Long> idpolls ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLLS", idpolls != null ? idpolls : null);

        List<Poll> x = this.jdbcTemplate. query(QUERY_FIND_ALL_BY_IDS, params, rowMapperNoOptions() );

        return x;
    }

    public Poll getById( Long idPoll ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLL", idPoll);

        List<Poll> x = this.jdbcTemplate.query(QUERY_FIND_BY_ID, params, rowMapper() );

        return x != null && x.size() > 0 ? x.get(0) : null;
    }

    public Poll save( Poll poll ) throws SQLException {
        setDataSource();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = getParamsInsert( poll );

        this.jdbcTemplate.update(QUERY_SAVE, params, keyHolder, new String[] { "idpoll" });

        Poll p = getById( keyHolder.getKey().longValue() );

        if (poll.getPollOptions() != null) {
            for (PollOption po : poll.getPollOptions()) {
                po.setPollId( p.getPollId() );
                serviceOptions.save(po);
            }
        }

        return p;
    }

    public Poll update( Poll poll ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = getParamsInsert( poll );

        this.jdbcTemplate.update(QUERY_UPDATE, params);

        if (poll.getPollOptions() != null) {
            for (PollOption po : poll.getPollOptions()) {
                serviceOptions.save(po);
            }
        }

        return poll;
    }

    public MapSqlParameterSource getParamsInsert( Poll poll ){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("TITLE", poll.getTitle());
        params.addValue("DESCRIPTION", poll.getDescription());
        params.addValue("IDUSER", poll.getUser() != null ? poll.getUser().getIdusuario() : null);
        params.addValue("REQUIRESAUTHENTICATION", poll.getRequiresAuthentication() );
        params.addValue("IDPOLL", poll.getPollId() );

        return params;
    }

    public RowMapper<Poll> rowMapper() throws SQLException {
        return new RowMapper<Poll>() {
            @Override
            public Poll mapRow(ResultSet rs, int rowNum) throws SQLException {
                Poll p = new Poll();
                Usuario u = new Usuario();

                p.setPollId(rs.getLong("IDPOLL"));
                p.setTitle(rs.getString("TITLE"));
                p.setDescription(rs.getString("DESCRIPTION"));
                p.setRequiresAuthentication(rs.getBoolean("REQUIRESAUTHENTICATION"));

                u.setIdusuario( rs.getLong("IDUSER") );
                p.setUser(u);

                p.setPollOptions( serviceOptions.getAllFromPoll( p.getPollId() ).getBody() );

                return p;
            }
        };
    }

    public RowMapper<Poll> rowMapperNoOptions() throws SQLException {
        return new RowMapper<Poll>() {
            @Override
            public Poll mapRow(ResultSet rs, int rowNum) throws SQLException {
                Poll p = new Poll();
                Usuario u = new Usuario();

                p.setPollId(rs.getLong("IDPOLL"));
                p.setTitle(rs.getString("TITLE"));
                p.setDescription(rs.getString("DESCRIPTION"));
                p.setRequiresAuthentication(rs.getBoolean("REQUIRESAUTHENTICATION"));

                u.setIdusuario( rs.getLong("IDUSER") );
                p.setUser(u);

                return p;
            }
        };
    }

    public void setDataSource() {
        SpringJdbcConfig config = new SpringJdbcConfig();
        this.jdbcTemplate = new NamedParameterJdbcTemplate( config.postgresDataSource() );
    }

}
