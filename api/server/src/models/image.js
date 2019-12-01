'use strict';
module.exports = (sequelize, DataTypes) => {
  const Image = sequelize.define('Image', {
    productId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    userId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    imageUrl: {
      type: DataTypes.STRING,
      allowNull: false
    },
  }, {});
  Image.associate = function(models) {
    Image.belongsTo(models.User, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Image.belongsTo(models.Product, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
  };
  return Image;
};