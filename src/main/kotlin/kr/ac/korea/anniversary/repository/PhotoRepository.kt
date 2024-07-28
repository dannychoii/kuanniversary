package kr.ac.korea.anniversary.repository

import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.repository.entity.Photo
import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class PhotoRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun findById(id: Long): Photo? {
        //language=sql
        val sql = "SELECT id, imageUrl, created_at \nFROM photo \nWHERE id = ?"
        return DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, { rs, _ ->
                Photo(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getLong(3),
                )
            }, id),
        )
    }

    fun search(pageCommand: PageCommand): Pair<List<Photo>, Long> {
        //language=sql
        val sql =
            "SELECT id, imageUrl, created_at \nFROM photo \nWHERE id = ?\nLIMIT ${pageCommand.pageSize} \nOFFSET ${pageCommand.page * pageCommand.pageSize} \n"
        //language=sql
        val countSql = "SELECT count(*) \nFROM photo"
        val totalCount = jdbcTemplate.query(countSql) { rs, _ -> rs.getLong(1) }.first()
        val resultList =
            jdbcTemplate.query(sql) { rs, _ ->
                Photo(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getLong(3),
                )
            }
        return Pair(resultList, totalCount)
    }
}
