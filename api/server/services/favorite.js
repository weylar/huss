import db from '../src/models';

class FavoriteService {
  static async createFavorite(req) {
    const adId = req.params.adId;
    const ad = await db.Product.findOne({ where: { id: adId } });

    if (!ad) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'There is no such ad'
      }
    }

    const isExist = await db.Favorite.findOne({ where: { userId: req.userId, id: adId } });
    if (isExist) {
      return {
        status: 'error',
        statusCode: 409,
        message: 'You already have this as your favorite'
      }
    }

    req.body.userId = req.userId;
    req.body.productId = adId;

    const favoriteAd = await db.Favorite.create(req.body);

    return {
      status: 'success',
      statusCode: 201,
      data: favoriteAd,
      message: 'Your favorite has been added successfully'
    }
  }

  static async getAFavorite(req) {
    const favorite = await db.Favorite.findOne({ where: { id: req.params.id } });

    if(!favorite) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'You don\'t have any favorite at the moment'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: favorite,
      message: 'Here is your favorite ad'
    }
  }

  static async getAllFavorites(req) {
    const favorites = await db.Favorite.findAll({ where: { userId: req.userId } });

    if(!favorites) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'You don\'t have any favorites at the moment'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: favorites,
      message: 'Here are your favorite ads'
    }
  }

  static async getAllAdFavorites(req) {
    const favorites = await db.Favorite.findAll({ where: { productId: req.params.adId } });

    if(!favorites) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No one has added this ad as favorite at the moment'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: favorites,
      message: 'Here are your favorite ads'
    }
  }
}

export default FavoriteService;