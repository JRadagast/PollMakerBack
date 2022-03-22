package com.demo.PollMakerBack.repository;

import com.demo.PollMakerBack.SpringJdbcConfig;
import com.demo.PollMakerBack.bean.PollOption;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository()
public class PollOptionRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static String QUERY_SAVE = "INSERT INTO polloptions (idpoll, option) VALUES " +
            "( :IDPOLL, :OPTION )";

    private static String QUERY_UPDATE = "UPDATE polloptions SET " +
            " idpoll = :IDPOLL, option = :OPTION WHERE idpolloptions = :IDPOLLOPTIONS ";

    private static String QUERY_FIND_ALL_BY_POLL = "SELECT * FROM polloptions WHERE idpoll = :IDPOLL";

    private static String QUERY_FIND_BY_ID = "SELECT * FROM polloptions WHERE idpolloptions = :IDPOLLOPTIONS";

    public List<PollOption> getAllFromPoll(Long idPoll) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLL", idPoll);

        List<PollOption> x = this.jdbcTemplate. query(QUERY_FIND_ALL_BY_POLL, params, rowMapper() );

        return x;
    }

    public PollOption getById( Long idPollOption ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLLOPTIONS", idPollOption);

        List<PollOption> x = this.jdbcTemplate.query(QUERY_FIND_BY_ID, params, rowMapper() );

        return x != null && x.size() > 0 ? x.get(0) : null;
    }

    public PollOption save( PollOption pollOption ) throws SQLException {
        setDataSource();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = getParamsInsert( pollOption );

        this.jdbcTemplate.update(QUERY_SAVE, params, keyHolder, new String[] { "idpolloptions" });

        PollOption p = getById( keyHolder.getKey().longValue() );

        return p;
    }

    public PollOption update( PollOption pollOption ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = getParamsInsert( pollOption );

        this.jdbcTemplate.update(QUERY_UPDATE, params);

        return pollOption;
    }

    public MapSqlParameterSource getParamsInsert( PollOption pollOption ){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLL", pollOption.getPollId());
        params.addValue("OPTION", pollOption.getOption());
        params.addValue("IDPOLLOPTIONS", pollOption.getPollOptionId());

        return params;
    }

    public RowMapper<PollOption> rowMapper() throws SQLException {
        return new RowMapper<PollOption>() {
            @Override
            public PollOption mapRow(ResultSet rs, int rowNum) throws SQLException {
                PollOption p = new PollOption();

                p.setPollId( rs.getLong("IDPOLL") );
                p.setOption( rs.getString("OPTION") );
                p.setPollOptionId( rs.getLong("IDPOLLOPTIONS"));

                return p;
            }
        };
    }

    public void setDataSource() {
        SpringJdbcConfig config = new SpringJdbcConfig();
        this.jdbcTemplate = new NamedParameterJdbcTemplate( config.postgresDataSource() );
    }

}
