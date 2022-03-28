package com.demo.PollMakerBack.repository;

import com.demo.PollMakerBack.SpringJdbcConfig;
import com.demo.PollMakerBack.bean.*;
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
public class PollAnswerRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static String QUERY_SAVE = "INSERT INTO pollanswers (iduser, idpoll, idpolloption ) VALUES " +
            "( :USERID, :POLLID, :IDPOLLOPTION )";

    private static String QUERY_UPDATE = "UPDATE pollanswers SET " +
            " idpolloption = :IDPOLLOPTION where idpoll = :POLLID AND iduser = :USERID";

    private static String QUERY_FIND_BY_POLL_AND_USER = "SELECT pa.*, po.\"option\" FROM pollanswers pa \n" +
            "INNER JOIN polloptions po ON pa.idpolloption = po.idpolloptions\n" +
            "WHERE pa.iduser = :USERID AND pa.idpoll = :POLLID";

    private static String QUERY_FIND_ALL_BY_USER = "SELECT p.*, pa.idpolloption, po.\"option\" FROM pollanswers pa \n" +
            "INNER JOIN poll p ON p.idpoll = pa.idpoll \n" +
            "INNER JOIN polloptions po ON po.idpolloptions = pa.idpolloption\n" +
            "WHERE pa.iduser = :USERID";

    private static String QUERY_FIND_BY_ID = "SELECT * FROM pollanswers WHERE idpollanswer = :IDPOLLANSWER";

    private static String QUERY_FIND_POLL_ANSWERS_COUNT = "SELECT po.\"option\" AS OPTION, COUNT(pa.idpollanswer) AS VALUE\n" +
            "FROM polloptions po \n" +
            "LEFT JOIN pollanswers pa ON pa.idpolloption = po.idpolloptions\n" +
            "WHERE po.idpoll = :POLLID\n" +
            "GROUP BY po.idpolloptions";

    public List<PollAnswer> getAllByUserId(Long iduser ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("USERID", iduser);

        List<PollAnswer> x = this.jdbcTemplate. query(QUERY_FIND_ALL_BY_USER, params, rowMapper() );

        return x;
    }

    public PollAnswer getById( Long idPollAnswer ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLLANSWER", idPollAnswer);

        List<PollAnswer> x = this.jdbcTemplate.query(QUERY_FIND_BY_ID, params, rowMapper() );

        return x != null && x.size() > 0 ? x.get(0) : null;
    }

    public PollAnswer getByPollIdAndUserId( Long idPoll, Long idUser ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("POLLID", idPoll);
        params.addValue("USERID", idUser);

        List<PollAnswer> x = this.jdbcTemplate.query(QUERY_FIND_BY_POLL_AND_USER, params, rowMapperWithOption() );

        return x != null && x.size() > 0 ? x.get(0) : null;
    }

    public List<BarChart> getChartData(Long idpoll ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("POLLID", idpoll);

        List<BarChart> x = this.jdbcTemplate. query(QUERY_FIND_POLL_ANSWERS_COUNT, params, rowMapperChart() );

        return x;
    }

    public PollAnswer save( PollAnswer pollAnswer ) throws SQLException {
        setDataSource();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = getParamsInsert( pollAnswer );

        this.jdbcTemplate.update(QUERY_SAVE, params, keyHolder, new String[] { "idpollanswer" });

        PollAnswer p = getById( keyHolder.getKey().longValue() );

        return p;
    }

    public PollAnswer update( PollAnswer pollAnswer ) throws SQLException {
        setDataSource();

        MapSqlParameterSource params = getParamsInsert( pollAnswer );

        this.jdbcTemplate.update(QUERY_UPDATE, params);

        return pollAnswer;
    }

    public MapSqlParameterSource getParamsInsert( PollAnswer pollAnswer ){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("IDPOLLOPTION", pollAnswer.getPollOptionId() );
        params.addValue("USERID", pollAnswer.getIduser());
        params.addValue("POLLID", pollAnswer.getPollId() );

        return params;
    }

    public RowMapper<PollAnswer> rowMapper() throws SQLException {
        return new RowMapper<PollAnswer>() {
            @Override
            public PollAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
                PollAnswer p = new PollAnswer();

                p.setPollId(rs.getLong("IDPOLL"));
                p.setPollAnswerId(rs.getLong("IDPOLLANSWER"));
                p.setIduser(rs.getLong("IDUSER"));
                p.setPollOptionId(rs.getLong("IDPOLLOPTION"));

                return p;
            }
        };
    }

    public RowMapper<PollAnswer> rowMapperWithOption() throws SQLException {
        return new RowMapper<PollAnswer>() {
            @Override
            public PollAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
                PollAnswer p = new PollAnswer();

                p.setPollId(rs.getLong("IDPOLL"));
                p.setPollAnswerId(rs.getLong("IDPOLLANSWER"));
                p.setIduser(rs.getLong("IDUSER"));
                p.setPollOptionId(rs.getLong("IDPOLLOPTION"));
                p.setOption( rs.getString("OPTION") );

                return p;
            }
        };
    }

    public RowMapper<BarChart> rowMapperChart() throws SQLException {
        return new RowMapper<BarChart>() {
            @Override
            public BarChart mapRow(ResultSet rs, int rowNum) throws SQLException {
                BarChart p = new BarChart();

                p.setLabel( rs.getString("OPTION") );
                p.setValue(rs.getLong("VALUE"));

                return p;
            }
        };
    }

    public void setDataSource() {
        SpringJdbcConfig config = new SpringJdbcConfig();
        this.jdbcTemplate = new NamedParameterJdbcTemplate( config.postgresDataSource() );
    }

}
