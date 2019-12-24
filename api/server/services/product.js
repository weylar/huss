import db from '../src/models';
import Sequelize from 'sequelize';

String.prototype.capitalize = function() {
  return this.charAt(0).toUpperCase() + this.slice(1);
};

class AdService {
  static async createAd(req) {
    const categoryId = req.params.categoryId;
    const subCategoryId = req.params.subCategoryId;

    const category = await db.Category.findOne({ where: { id: categoryId } });
    const subCategory = await db.SubCategory.findOne({ where: { id: subCategoryId } });

    if (!category) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No category with such parameter'
      };
    }

    if (!subCategory) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'No sub category with such parameter'
      };
    }

    req.body.categoryId = categoryId;
    req.body.subCategoryId = subCategoryId;
    req.body.userId = req.userId;

    const ad = await db.Product.create(req.body);

    return {
      status: 'success',
      statusCode: 200,
      data: { category, subCategory, ad },
      message: 'A new ad has been added'
    };
  }

  static async getAd(req) {
    const oldAd = await db.Product.findOne({ where: { id: req.params.adId } });

    if (!oldAd) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'Such ad does not exist'
      };
    }

    const editViewCount = await db.Product.update(
      { count: oldAd.count + 1 },
      { where: { id: req.params.adId } }
    );

    if (editViewCount[0] === 1) {
      const foundAd = await db.Product.findOne({ where: { id: req.params.adId } });
      const foundSubCategory = await db.SubCategory.findOne({
        where: { id: foundAd.subCategoryId }
      });
      const category = await db.Category.findOne({ where: { id: foundAd.categoryId } });

      return {
        status: 'success',
        statusCode: 200,
        data: { foundAd, foundSubCategory, category },
        message: 'Ad sucessfully retrieved'
      };
    }
  }

  static async getOwnAd(req) {
    const foundAd = await db.Product.findOne({
      where: { userId: req.userId, id: req.params.adId }
    });

    if (foundAd) {
      const foundSubCategory = await db.SubCategory.findOne({
        where: { id: foundAd.subCategoryId }
      });
      const category = await db.Category.findOne({ where: { id: foundAd.categoryId } });

      return {
        status: 'success',
        statusCode: 200,
        data: { foundAd, foundSubCategory, category },
        message: 'Ad sucessfully retrieved'
      };
    }

    return {
      status: 'error',
      statusCode: 404,
      message: 'Such ad does not exist for you'
    };
  }

  static async getAllAds() {
    const allAds = await db.Product.findAll({ order: [['id', 'DESC']] });

    return {
      status: 'success',
      statusCode: 200,
      data: allAds,
      message: 'All ads have been retrieved successfully'
    };
  }

  static async getAllAdsByLimit(req) {
    const allAds = await db.Product.findAll({
      limit: req.params.limit,
      order: [['id', 'DESC']]
    });

    return {
      status: 'success',
      statusCode: 200,
      data: allAds,
      message: 'All ads retrieved successfully'
    };
  }

  static async paginateAds(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      order: [['id', 'DESC']]
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getAdsSuggest(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    let title = req.params.title;
    title = title.capitalize();
    const Op = Sequelize.Op;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      where: { title: { [Op.startsWith]: `%${title}%` } }
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getAdsByStatus(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      where: { status: req.params.status },
      order: [['id', 'DESC']]
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getAdsByStatusSuggest(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    let title = req.params.title;
    title = title.capitalize();
    const Op = Sequelize.Op;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      where: { status: req.params.status, title: { [Op.startsWith]: `%${title}%` } }
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getAllOwnAds(req) {
    const allOwnAds = await db.Product.findAll({
      where: { userId: req.userId },
      order: [['id', 'DESC']]
    });

    if (allOwnAds.length === 0) {
      return {
        status: 'error',
        statusCode: 404,
        message: 'You do not have any ads'
      };
    }
    return {
      status: 'success',
      statusCode: 200,
      data: allOwnAds,
      message: 'All your ads have been successfully retrieved'
    };
  }

  static async getAllOwnAdsByLimit(req) {
    const allAds = await db.Product.findAll({
      where: { userId: req.userId },
      limit: req.params.limit,
      order: [['id', 'DESC']]
    });

    return {
      status: 'success',
      statusCode: 200,
      data: allAds,
      message: 'All ads retrieved successfully'
    };
  }

  static async paginateOwnAds(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    const allAds = await db.Product.findAll({
      where: { userId: req.userId },
      offset,
      limit,
      order: [['id', 'DESC']]
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getOwnAdsSuggest(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    let title = req.params.title;
    title = title.capitalize();
    const Op = Sequelize.Op;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      where: { userId: req.userId, title: { [Op.startsWith]: `%${title}%` } }
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getOwnAdsByStatus(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      where: { userId: req.userId, status: req.params.status },
      order: [['id', 'DESC']]
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async getOwnAdsByStatusSuggest(req) {
    const limit = req.params.limit;
    const offset = req.params.offset;
    let title = req.params.title;
    title = title.capitalize();
    const Op = Sequelize.Op;
    const allAds = await db.Product.findAll({
      offset,
      limit,
      where: {
        userId: req.userId,
        status: req.params.status,
        title: { [Op.startsWith]: `%${title}%` }
      }
    });

    if (allAds) {
      return {
        status: 'success',
        statusCode: 200,
        data: allAds,
        message: 'All ads retrieved successfully'
      };
    }
  }

  static async makeAdInactive(req) {
    const ad = await db.Product.findOne({ where: { id: req.params.adId } });

    if (!ad) {
      return {
        status: 'error',
        statusCode: 404,
        message: ' Such ad was not found'
      };
    }

    const today = new Date();
    const diffInTime = today.getTime() - ad.createdAt.getTime();
    const diffInDays = diffInTime/(1000 * 3600 * 24);

    if (ad.type === 'free' && diffInDays > 7) {
      await db.Product.update({ status: 'inactive' }, { where: { id: req.params.adId } });
    }

    const newAd = await db.Product.findOne({ where: { id: req.params.adId } });

    return {
      status: 'success',
      statusCode: 200,
      data: newAd,
    }
  }
}

export default AdService;
