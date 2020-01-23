'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.addColumn(
        'Products',
        'modifiedStatusDate',
        {
          type: Sequelize.DATE,
          allowNull: true
        }
      )
  },

  down: (queryInterface, Sequelize) => {
    return queryInterface.removeColumn(
        'Products',
        'modifiedStatusDate'
      )
  }
};
