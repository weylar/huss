import FavoriteService from '../services/favorite';

class FavoriteController {
  static async createFavorite(req, res, next) {
    try {
      const response = await FavoriteService.createFavorite(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAFavorite(req, res, next) {
    try {
      const response = await FavoriteService.getAFavorite(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllFavorites(req, res, next) {
    try {
      const response = await FavoriteService.getAllFavorites(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllAdFavorites(req, res, next) {
    try {
      const response = await FavoriteService.getAllAdFavorites(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default FavoriteController;