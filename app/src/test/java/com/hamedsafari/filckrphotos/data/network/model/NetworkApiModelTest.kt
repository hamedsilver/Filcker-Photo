package com.hamedsafari.filckrphotos.data.network.model

import com.hamedsafari.filckrphotos.mockData.FakePhotos
import com.hamedsafari.filckrphotos.data.repository.mappers.toDomainModel
import org.junit.Assert.*
import org.junit.Test

class NetworkApiModelTest{

    @Test
    fun network_PhotosMetaDataApiModel_can_be_mapped_to_domain_photo() {
        val networkModel = FakePhotos.photo.photos
        val entity = networkModel

        assertEquals(1, entity.page)
        assertEquals(1, entity.pages)
        assertEquals(1, entity.perPage)
        assertEquals(1, entity.total)
        assertEquals(listOf(PhotoApiModel(
            id = "test_id",
            owner = "test_owner",
            secret = "test_secret",
            server = "test_server",
            farm = 1,
            title = "test_title"
        )), entity.photo)
    }

    @Test
    fun network_PhotoApiModel_can_be_mapped_to_domain_photo() {
        val networkModel = FakePhotos.photo.photos.photo
        val entity = networkModel.map { it.toDomainModel() }.first()

        assertEquals("test_id", entity.id)
        assertEquals("test_title", entity.title)
        assertEquals("https://farm1.staticflickr.com/test_server/test_id_test_secret_b.jpg", entity.image_url)
        assertEquals("https://farm1.staticflickr.com/test_server/test_id_test_secret_t.jpg", entity.thumbnail_url)
    }
}