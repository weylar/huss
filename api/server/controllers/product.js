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

  static async getAllOwnAdsByLimit(req,res,next) {
    try {
      const response = await AdService.getAllOwnAdsByLimit(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async paginateAds(req,res,next) {
    try {
      const response = await AdService.paginateAds(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async paginateOwnAds(req,res,next) {
    try {
      const response = await AdService.paginateOwnAds(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAdsSuggest(req,res,next) {
    try {
      const response = await AdService.getAdsSuggest(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getOwnAdsSuggest(req,res,next) {
    try {
      const response = await AdService.getOwnAdsSuggest(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAdsByStatus(req,res,next) {
    try {
      const response = await AdService.getAdsByStatus(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getOwnAdsByStatus(req,res,next) {
    try {
      const response = await AdService.getOwnAdsByStatus(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getOwnAdsByStatusSuggest(req,res,next) {
    try {
      const response = await AdService.getOwnAdsByStatusSuggest(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAdsByStatusSuggest(req,res,next) {
    try {
      const response = await AdService.getAdsByStatusSuggest(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async makeAdInactive(req,res,next) {
    try {
      const response = await AdService.makeAdInactive(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async makePayment(req,res,next) {
    try {
      const response = await AdService.makePayment(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async deactivatePayment(req,res,next) {
    try {
      const response = await AdService.deactivatePayment(req);
      
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default AdController;