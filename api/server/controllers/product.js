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
}

export default AdController;