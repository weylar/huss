'use strict';
module.exports = (sequelize, DataTypes) => {
  const Product = sequelize.define('Product', {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    categoryId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    subCategoryId: {
      type: DataTypes.INTEGER,
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
      allowNull: false
    },
    status: {
      type: DataTypes.STRING,
      allowNull: false
    },
    price: {
      type: DataTypes.STRING,
      allowNull: false
    },
    isNegotiable: {
      type: DataTypes.STRING,
      allowNull: false
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
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Product.belongsTo(models.SubCategory, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
  };
  return Product;
};
