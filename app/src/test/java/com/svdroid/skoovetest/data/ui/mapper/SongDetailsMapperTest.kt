package com.svdroid.skoovetest.data.ui.mapper

import com.svdroid.skoovetest.data.api.SongsDetailsResponse
import com.svdroid.skoovetest.data.db.SongDetailsEntity
import org.junit.Assert.assertEquals
import org.junit.Test


class SongDetailsMapperTest {

    private val entity = SongDetailsEntity(
        id = 1L,
        title = "Test Song",
        audioUrl = "https://example.com/audio.mp3",
        coverUrl = "https://example.com/cover.jpg",
        duration = 180,
        isFavorite = true,
        rating = 4,
    )

    private val response = SongsDetailsResponse(
        title = "Test Song 2",
        audio = "https://example.com/audio2.mp3",
        cover = "https://example.com/cover2.jpg",
        duration = 240,
    )

    @Test
    fun testToUIModel() {
        val expectedUIModel = entity.toUIModel()

        assertEquals(entity.id, expectedUIModel.id)
        assertEquals(entity.title, expectedUIModel.title)
        assertEquals(entity.audioUrl, expectedUIModel.audioUrl)
        assertEquals(entity.coverUrl, expectedUIModel.coverUrl)
        assertEquals(entity.duration, expectedUIModel.duration)
        assertEquals(entity.isFavorite, expectedUIModel.isFavorite)
        assertEquals(entity.rating, expectedUIModel.rating)
    }

    @Test
    fun testToDbEntity() {
        val mockedId = 0L
        val mockedFavorites = true
        val mockedRating = 5
        val expectedDBEntity = response.toDbEntity(isFavorite = mockedFavorites, rating = mockedRating)

        assertEquals(mockedId, expectedDBEntity.id)
        assertEquals(response.title, expectedDBEntity.title)
        assertEquals(response.audio, expectedDBEntity.audioUrl)
        assertEquals(response.cover, expectedDBEntity.coverUrl)
        assertEquals(response.duration, expectedDBEntity.duration)
        assertEquals(mockedFavorites, expectedDBEntity.isFavorite)
        assertEquals(mockedRating, expectedDBEntity.rating)
    }

    @Test
    fun testToDbEntityWithoutParameters() {
        val mockedId = 0L
        val mockedFavorites = null
        val mockedRating = null
        val expectedDBEntity = response.toDbEntity()

        assertEquals(mockedId, expectedDBEntity.id)
        assertEquals(response.title, expectedDBEntity.title)
        assertEquals(response.audio, expectedDBEntity.audioUrl)
        assertEquals(response.cover, expectedDBEntity.coverUrl)
        assertEquals(response.duration, expectedDBEntity.duration)
        assertEquals(mockedFavorites, expectedDBEntity.isFavorite)
        assertEquals(mockedRating, expectedDBEntity.rating)
    }
}