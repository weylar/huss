import adReportService from '../services/adReport';

class AdReportController {
  static async createAdReport(req, res, next) {
    try {
      const response = await adReportService.createAdReport(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAdReport(req, res, next) {
    try {
      const response = await adReportService.getAdReport(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getAllAdsReports(req, res, next) {
    try {
      const response = await adReportService.getAllAdsReports(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getReportsOfASingleAd(req, res, next) {
    try {
      const response = await adReportService.getReportsOfASingleAd(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async getReportsOfASingleUser(req, res, next) {
    try {
      const response = await adReportService.getReportsOfASingleUser(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }

  static async deleteAnAdReport(req, res, next) {
    try {
      const response = await adReportService.deleteAnAdReport(req);
      return res.status(response.statusCode).json(response);
    } catch (e) {
      return next(e);
    }
  }
}

export default AdReportController;