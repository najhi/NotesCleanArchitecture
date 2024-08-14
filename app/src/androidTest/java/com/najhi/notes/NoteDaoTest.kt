package com.najhi.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.najhi.notes.data.datastore.NoteDao
import com.najhi.notes.data.datastore.NoteDatabase
import com.najhi.notes.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * @author Muhammad Najhi Ullah
 *         MozgoTech
 *         Author Email: najhi1989@gmail.com
 *         Created on: 13/08/2024
 */
class NoteDaoTest
{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()

        noteDao = database.noteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetNotesByTitleAsc() = runBlocking {
        val note1 = Note(title = "B", content = "Content B", tags = "", created = 1L, modified = 1L, starred = false)
        val note2 = Note(title = "A", content = "Content A", tags = "", created = 2L, modified = 2L, starred = false)

        noteDao.insert(note1)
        noteDao.insert(note2)

        val notes = noteDao.getNotesByTitleAsc().getOrAwaitValue()

        Assert.assertEquals("A", notes[0].title)
        Assert.assertEquals("B", notes[1].title)
    }

    @Test
    fun insertAndGetNotesByCreatedDesc() = runBlocking {
        val note1 = Note(title = "A", content = "Content A", tags = "", created = 1L, modified = 1L, starred = false)
        val note2 = Note(title = "B", content = "Content B", tags = "", created = 2L, modified = 2L, starred = false)

        noteDao.insert(note1)
        noteDao.insert(note2)

        val notes = noteDao.getNotesByCreatedDesc().getOrAwaitValue()

        Assert.assertEquals(2L, notes[0].created)
        Assert.assertEquals(1L, notes[1].created)
    }

    @Test
    fun insertAndSearchNotes() = runBlocking {
        val note1 = Note(title = "Hello World", content = "Content 1", tags = "", created = 1L, modified = 1L, starred = false)
        val note2 = Note(title = "Test Note", content = "Content 2", tags = "", created = 2L, modified = 2L, starred = false)

        noteDao.insert(note1)
        noteDao.insert(note2)

        val notes = noteDao.searchNotes("%Hello%").getOrAwaitValue()

        Assert.assertEquals(1, notes.size)
        Assert.assertEquals("Hello World", notes[0].title)
    }

    @Test
    fun insertUpdateAndCheckNote() = runBlocking {
        val note = Note(title = "Initial Title", content = "Initial Content", tags = "", created = 1L, modified = 1L, starred = false)

        noteDao.insert(note)

        val insertedNote = noteDao.getNotesByTitleAsc().getOrAwaitValue().first()
        Assert.assertEquals("Initial Title", insertedNote.title)

        val updatedNote = insertedNote.copy(title = "Updated Title")
        noteDao.update(updatedNote)

        val updatedNotes = noteDao.getNotesByTitleAsc().getOrAwaitValue()
        Assert.assertEquals("Updated Title", updatedNotes.first().title)
    }

    @Test
    fun insertAndDeleteNote() = runBlocking {
        val note1 = Note(title = "Title 1", content = "Content 1", tags = "", created = 1L, modified = 1L, starred = false)
        val note2 = Note(title = "Title 2", content = "Content 2", tags = "", created = 2L, modified = 2L, starred = false)

        noteDao.insert(note1)
        noteDao.insert(note2)

        var notes = noteDao.getNotesByTitleAsc().getOrAwaitValue()
        Assert.assertEquals(2, notes.size)

        noteDao.delete(listOf(notes.first()))

        notes = noteDao.getNotesByTitleAsc().getOrAwaitValue()
        Assert.assertEquals(1, notes.size)
        Assert.assertEquals("Title 2", notes[0].title)
    }

    private fun <T> LiveData<T>.getOrAwaitValue(): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T>
        {
            override fun onChanged(value: T)
            {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        try {
            latch.await(2, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            throw AssertionError("LiveData value was never set.")
        }

        return data ?: throw AssertionError("LiveData value was null.")
    }
}