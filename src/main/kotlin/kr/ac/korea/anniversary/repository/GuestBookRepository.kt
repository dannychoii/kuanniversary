package kr.ac.korea.anniversary.repository

import kr.ac.korea.anniversary.global.CustomException
import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.helper.TimeHelper
import kr.ac.korea.anniversary.repository.entity.GuestBook
import kr.ac.korea.anniversary.service.dto.command.GuestBookSearchCommand
import org.springframework.dao.support.DataAccessUtils
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository

@Repository
class GuestBookRepository(
    private val jdbcTemplate: JdbcTemplate,
) {
    fun findById(id: Long): GuestBook? {
        //language=sql
        val sql = "SELECT id, head, content, is_confirmed, created_at, updated_at\nFROM guest_book\nWHERE id = ?"
        return DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, { rs, _ ->
                GuestBook(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBoolean(5),
                    rs.getLong(6),
                    rs.getLong(7),
                )
            }, id),
        )
    }

    fun findByIdAndIsConfirmed(id: Long, isConfirmed: Boolean): GuestBook? {
        //language=sql
        val sql = "SELECT id, head, content, is_confirmed, created_at, updated_at\nFROM guest_book\nWHERE id = ? AND is_confirmed = ?"
        return DataAccessUtils.singleResult(
            jdbcTemplate.query(sql, { rs, _ ->
                GuestBook(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBoolean(5),
                    rs.getLong(6),
                    rs.getLong(7),
                )
            }, id),
        )
    }

    fun search(
        command: GuestBookSearchCommand,
        pageCommand: PageCommand,
    ): Pair<List<GuestBook>, Long> {
        val (conditonString, preparedStatement) = getConditionStringAndPreparedStatement(command)
        // language= sql
        val sql =
            "SELECT id, head, content, is_confirmed, created_at, updated_at FROM guest_book WHERE 1=1 $conditonString ORDER BY id ${if (pageCommand.isDesc) "DESC" else "ASC"} LIMIT ${pageCommand.pageSize} OFFSET ${pageCommand.page * pageCommand.pageSize}"
        val countSql = "SELECT count(*) FROM guest_book WHERE 1= 1 $conditonString "

        val totalCount = jdbcTemplate.query(countSql, preparedStatement) { rs, _ -> rs.getLong(1) }.first()
        val resultList =
            jdbcTemplate.query(sql, preparedStatement) { rs, _ ->
                GuestBook(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getBoolean(5),
                    rs.getLong(6),
                    rs.getLong(7),
                )
            }

        return Pair(resultList, totalCount)
    }

    private fun getConditionStringAndPreparedStatement(command: GuestBookSearchCommand): Pair<String, PreparedStatementSetter> {
        val preparedStatementSetter =
            PreparedStatementSetter {
                val params =
                    listOfNotNull(
                        command.isConfirmed,
                        command.fromTs,
                        command.toTs,
                    )
                params.forEachIndexed { index, param -> it.setObject(index + 1, param) }
            }
        val stringBuilder = StringBuilder()

        stringBuilder.append(" AND is_confirmed = ? ")

        if (command.fromTs != null) {
            stringBuilder.append(" AND created_at >= ? ")
        }
        if (command.toTs != null) {
            stringBuilder.append(" AND created_at < ? ")
        }

        return Pair(stringBuilder.toString(), preparedStatementSetter)
    }

    fun insert(guestBook: GuestBook): GuestBook {
        val params = HashMap<String, Any?>()
        params["head"] = guestBook.head
        params["content"] = guestBook.content
        params["writer"] = guestBook.writer
        params["is_confirmed"] = guestBook.isConfirmed
        params["created_at"] = guestBook.createdAt
        params["updated_at"] = guestBook.updatedAt

        val id =
            SimpleJdbcInsert(jdbcTemplate)
                .withTableName("guest_book")
                .usingColumns("head", "content", "writer", "is_confirmed", "created_at", "updated_at")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(params)
        return this.findById(id.toLong())
            ?: throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong")
    }

    fun updateConfirm(
        id: Long,
        isConfirmed: Boolean,
    ) {
        //language=sql
        val sql = "UPDATE guest_book SET is_confirmed = ?, updated_at = ${TimeHelper.nowKstTimeStamp()} WHERE id = ?"
        jdbcTemplate.update(sql, isConfirmed, id)
    }

    fun deleteById(id: Long) {
        // language=sql
        val sql = "DELETE FROM guest_book WHERE id = ?"
        jdbcTemplate.update(sql, id)
    }
}
