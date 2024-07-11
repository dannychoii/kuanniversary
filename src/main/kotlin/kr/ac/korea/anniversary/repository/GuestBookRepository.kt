package kr.ac.korea.anniversary.repository

import kr.ac.korea.anniversary.global.CustomException
import kr.ac.korea.anniversary.repository.entity.GuestBook
import kr.ac.korea.anniversary.service.dto.command.GuestBookPageCommand
import kr.ac.korea.anniversary.service.dto.command.GuestBookSearchCommand
import org.springframework.dao.support.DataAccessUtils
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository

@Repository
class GuestBookRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun findById(id: Long): GuestBook? {
        //language=sql
        val sql = "SELECT id, head, content, isVisible, createdAt, updatedAt\nFROM guest_book\nWHERE id = ?"
        return DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, { rs, _ ->
                GuestBook(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getBoolean(4),
                    rs.getLong(5),
                    rs.getLong(6)
                )
            }, id)
        )
    }

    fun search(command: GuestBookSearchCommand, pageCommand: GuestBookPageCommand): Pair<List<GuestBook>, Long> {
        val (conditonString, preparedStatement) = getConditionStringAndPreparedStatement(command)
        // language= sql
        val sql = "SELECT id, head, content, isVisible, createdAt, updatedAt\nFROM guest_book\nWHERE 1= 1 $conditonString ORDER BY id ${if (pageCommand.isDesc) "DESC" else "ASC"}LIMIT ${pageCommand.pageSize} OFFSET ${pageCommand.page * pageCommand.pageSize}"
        val countSql = "SELECT id, head, content, isVisible, createdAt, updatedAt\nFROM guest_book\nWHERE 1= 1 $conditonString "

        val totalCount = jdbcTemplate.query(countSql, preparedStatement) { rs, _ -> rs.getLong(1) }.first()
        val resultList = jdbcTemplate.query(sql, preparedStatement) { rs, _ ->
            GuestBook(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getBoolean(4),
                rs.getLong(5),
                rs.getLong(6)
            )
        }
        return Pair(resultList, totalCount)
    }

    private fun getConditionStringAndPreparedStatement(command: GuestBookSearchCommand): Pair<String, PreparedStatementSetter> {
        val preparedStatementSetter = PreparedStatementSetter {
            val params = listOfNotNull(
                command.fromTs,
                command.toTs
            )
            params.forEachIndexed { index, param -> it.setObject(index + 1, param) }
        }
        val stringBuilder = StringBuilder()
        if (command.fromTs != null) {
            stringBuilder.append("AND from_ts >= ?")
        }
        if (command.toTs != null) {
            stringBuilder.append("AND to_ts < ?")
        }

        return Pair(stringBuilder.toString(), preparedStatementSetter)
    }

    fun insert(guestBook: GuestBook): GuestBook {
        val params = HashMap<String, Any?>()
        params["head"] = guestBook.head
        params["content"] = guestBook.content

        val id = SimpleJdbcInsert(jdbcTemplate)
            .withTableName("guest_book")
            .usingColumns("head")
            .usingColumns("content")
            .executeAndReturnKey(params)
        return this.findById(id.toLong())
            ?: throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong")
    }

    fun deleteById(id: Long) {
        // language=sql
        val sql = "DELETE FROM guest_book WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }
}