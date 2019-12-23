import AdService from '../services/product';

class AdController {
  static async createAd(req,res,next) {
    try {
      const response = await AdService.createAd(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAd(req,res,next) {
    try {
      const response = await AdService.getAd(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getOwnAd(req,res,next) {
    try {
      const response = await AdService.getOwnAd(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllAds(req,res,next) {
    try {
      const response = await AdService.getAllAds(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllOwnAds(req,res,next) {
    try {
      const response = await AdService.getAllOwnAds(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllAdsByLimit(req,res,next) {
    try {
      const response = await AdService.getAllAdsByLimit(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default AdController;