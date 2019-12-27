import db from '../src/models';

class AdImageService {
  static async createAdImage(req) {
    const adId = req.params.adId;
    const ad = await db.Product.findOne({ where: { userId: req.userId, id: adId } });

    if (!ad) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'There is no such ad'
      }
    }

    req.body.userId = req.userId;
    req.body.productId = adId;

    const adImage = await db.Image.create(req.body);

    return {
      status: 'success',
      statusCode: 201,
      data: adImage,
      message: 'Ad image has been added successfully'
    }
  } 

  static async getAnImage(req) {
    const adImage = await db.Image.findOne({ where: { id: req.params.imageId } });

    if(!adImage) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No ad with such image'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: adImage,
      message: 'Image retrieved successfully'
    }
  }

  static async getAnAdImages(req) {
    const adImages = await db.Image.findAll({ where: { productId: req.params.adId } });

    if (!adImages) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'There are no images for this ad'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: adImages,
      message: 'All ad images retrieved successfully'
    }
  }

  static async getAllImages() {
    const adImages = await db.Image.findAll();

    if (!adImages) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'There are no images'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: adImages,
      message: 'All ad images retrieved successfully'
    }
  }

  static async deleteImage(req) {
    const deletedAdImage = await db.Image.destroy(
      { where: { userId: req.userId, id: req.params.imageId } }
    );

    if(!deletedAdImage) {
      return {
        status: 'error',
        statusCode: 403,
        message: 'You cannot delete this image, there\'s no image as such for you'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      message: 'Ad image has been successfully deleted'
    }
  }
}

export default AdImageService;