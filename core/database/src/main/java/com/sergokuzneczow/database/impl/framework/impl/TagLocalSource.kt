package com.sergokuzneczow.database.impl.framework.impl

import com.sergokuzneczow.database.impl.framework.dao.TagDao

public class TagLocalSource private constructor(
    private val tagDao: TagDao,
) {

//    @Inject
//    public constructor(
//        roomHelper: RoomHelper,
//    ) : this(
//        tagDao = roomHelper.provideTagDao(),
//    )
//
//    override fun getPictureTagsByPictureKey(pictureKey: String): Flow<List<TagLocalModel>> {
//        TODO("Not yet implemented")
//    }
//
//    override fun setPictureTags(
//        pictureKey: String,
//        tags: List<TagLocalModel>,
//    ) {
//        TODO("Not yet implemented")
//    }
}