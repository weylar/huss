'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.addColumn(
      'Categories',
      'belongedAd',
      {
        type: Sequelize.INTEGER,
        allowNull: true,
        defaultValue: 0
      }
    )
  },

  down: (queryInterface) => {
   return queryInterface.removeColumn(
    'Categories',
    'belongedAd'
  )
  }
};
