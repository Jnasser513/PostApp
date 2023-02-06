package latmobile.app.postapp.framework.databasemanager.mappers

import latmobile.app.postapp.domain.response.PostImageResponse
import latmobile.app.postapp.framework.databasemanager.entity.PostImageEntity

fun List<PostImageResponse>.toPostImageEntityList() = map(PostImageResponse::toPostImageEntity)

fun PostImageResponse.toPostImageEntity() = PostImageEntity(
    id,
    albumId,
    title,
    url,
    thumbnailUrl
)