import AdImageService from '../services/image'

class AdImageController {
  static async createAdImage(req, res, next) {
    try {
      const response = await AdImageService.createAdImage(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAnImage(req, res, next) {
    try {
      const response = await AdImageService.getAnImage(req);

      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAnAdImages(req, res, next) {
    try {
      const response = await AdImageService.getAnAdImages(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllImages(req, res, next) {
    try {
      const response = await AdImageService.getAllImages(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default AdImageController;