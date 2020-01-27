import db from '../src/models';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
}

class AdReportService {
  static async createAdReport(req) {
    req.body.reporterId = req.userId;
    
    if (req.body.productId) {
      const productId = req.body.productId;
      const ad = await db.Product.findOne({ where: { id: productId }, attributes: { exclude: 'name' }});      

      if (!ad) {
        return {
          status: 'error',
          statusCode: 404,
          message: 'Such ad does not exist'
        }
      }
      req.body.productId = productId;
      req.body.userId = ad.dataValues.userId;

      
      const adReport = await db.AdReport.create(req.body);

      return {
        status: 'success',
        statusCode: 201,
        data: adReport,
        message: 'Ad has been successfully reported'
      }
    }

    const userId = req.body.userId;
    req.body.userId = userId;

    const adReport = await db.AdReport.create(req.body);

    return {
      status: 'success',
      statusCode: 201,
      data: adReport,
      message: 'Ad has been successfully reported'
    }
  }

  static async getAdReport(req) {

    const adReportId = req.params.id;

    const adReport = await db.AdReport.findOne({ where: { id: adReportId }});

    if (!adReport) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'Such report does not exist'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: adReport,
      message: 'Ad report retrieved successfully'
    }
  }

  static async getAllAdsReports() {

    const adsReports = await db.AdReport.findAll();

    return {
      status: 'status',
      statusCode: 200,
      data: adsReports,
      message: 'All ads reports retrieved successfully'
    }
  }

  static async getReportsOfASingleAd(req) {
    const singleAdReports = await db.AdReport.findAll({ where: {productId: req.params.adId}});

    if (!singleAdReports) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'This ad has not been reported'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: singleAdReports,
      message: 'All reports retrieved successfully'
    }
  }

  static async getReportsOfASingleUser(req) {
    const singleUserReports = await db.AdReport.findAll({ where: {userId: req.params.userId}});

    if (!singleUserReports) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'This user has not been reported'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      data: singleUserReports,
      message: 'All reports retrieved successfully'
    }
  }

  static async deleteAnAdReport(req) {
    const toBeDeleted = await db.AdReport.destroy({ where: {id: req.params.adId}});

    if (!toBeDeleted) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No report with such id'
      }
    }

    return {
      status: 'success',
      statusCode: 200,
      message: 'This ad has been successfully deleted'
    }
  }
}

export default AdReportService;