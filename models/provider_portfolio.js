/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('providerPortfolio', {
		id: {
			type: DataTypes.INTEGER(11),
			allowNull: true,
			primaryKey: true,
			autoIncrement: true,
			field: 'id'
		},
		providerId: {
			type: DataTypes.INTEGER(11),
			allowNull: true,
			field: 'provider_id'
		},
		title: {
			type: DataTypes.STRING(255),
			allowNull: true,
			field: 'title'
		},
		description: {
			type: DataTypes.TEXT,
			allowNull: true,
			field: 'description'
		},
		status: {
			type: DataTypes.INTEGER(11),
			allowNull: true,
			field: 'status'
		},
		image: {
			type: DataTypes.STRING(255),
			allowNull: true,
			field: 'image'
		},
		createdAt: {
			type: DataTypes.DATE,
			allowNull: true,
			defaultValue: sequelize.fn('current_timestamp'),
			field: 'created_at'
		},
		updatedAt: {
			type: DataTypes.DATE,
			allowNull: true,
			defaultValue: sequelize.fn('current_timestamp'),
			field: 'updated_at'
		}
	}, {
		tableName: 'provider_portfolio'
	});
};
