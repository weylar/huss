'use strict';
module.exports = (sequelize, DataTypes) => {
  const Favorite = sequelize.define('Favorite', {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    productId: {
      type: DataTypes.INTEGER,
      allowNulll: true
    }
  }, {});
  Favorite.associate = function(models) {
    // associations can be defined here
    Favorite.belongsTo(model.User, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    });
    Favorite.belongsTo(model.Product, {
      foreignKey: 'id',
      onUpdate: 'CASCADE',
      onDelete: 'CASCADE'
    })
  };
  return Favorite;
};