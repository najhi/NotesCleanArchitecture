package com.najhi.notes.data.datastore

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.najhi.notes.domain.model.Note
import com.najhi.notes.presentation.OrderBy
import com.najhi.notes.presentation.SortBy

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 21/07/2024
 */

@Dao
interface NoteDao
{
    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getNotesByTitleAsc(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY title DESC")
    fun getNotesByTitleDesc(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY created ASC")
    fun getNotesByCreatedAsc(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY created DESC")
    fun getNotesByCreatedDesc(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY modified ASC")
    fun getNotesByModifiedAsc(): LiveData<List<Note>>

    @Query("SELECT * FROM notes ORDER BY modified DESC")
    fun getNotesByModifiedDesc(): LiveData<List<Note>>

    fun getAllNotes(sortBy: String, orderBy: String): LiveData<List<Note>> {
        return when (sortBy) {
            SortBy.TITLE.displayName -> {
                when (orderBy) {
                    OrderBy.ASCENDING.name -> getNotesByTitleAsc()
                    else -> getNotesByTitleDesc()
                }
            }
            SortBy.DATE_CREATED.displayName -> {
                when (orderBy) {
                    OrderBy.ASCENDING.name -> getNotesByCreatedAsc()
                    else -> getNotesByCreatedDesc()
                }
            }
            else -> {
                when (orderBy) {
                    OrderBy.ASCENDING.name -> getNotesByModifiedAsc()
                    else -> getNotesByModifiedDesc()
                }
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(notes: List<Note>)

    @Query("SELECT * FROM notes WHERE title LIKE :query OR content LIKE :query ORDER BY created DESC")
    fun searchNotes(query: String): LiveData<List<Note>>
}