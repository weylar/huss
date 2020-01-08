'use strict';
module.exports = (sequelize, DataTypes) => {
  const Product = sequelize.define('Product', {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    categoryName: {
      type: DataTypes.STRING,
      allowNull: true
    },
    subCategoryName: {
      type: DataTypes.STRING,
      allowNull: true
    },
    title: {
      type: DataTypes.STRING,
      allowNull: false
    },
    description: {
      type: DataTypes.STRING,
      allowNull: false
    },
    type: {
      type: DataTypes.STRING,
      allowNull: true
    },
    status: {
      type: DataTypes.STRING,
      allowNull: true
    },
    price: {
      type: DataTypes.STRING,
      allowNull: false
    },
    isNegotiable: {
      type: DataTypes.BOOLEAN,
      allowNull: true
    },
    count: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    payDate: {
      type: DataTypes.DATE,
      allowNull: true
    },
    location: {
      type: DataTypes.STRING,
      allowNull: true
    }
  }, {});
  Product.associate = function(models) {
    Product.hasMany(models.Image, {
      foreignKey: 'productId',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Product.hasMany(models.Favorite, {
      foreignKey: 'productId',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Product.belongsTo(models.User, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Product.belongsTo(models.Category, {
      foreignKey: 'name',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Product.belongsTo(models.SubCategory, {
      foreignKey: 'name',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
  };
  return Product;
};
