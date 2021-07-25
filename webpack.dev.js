'use strict';

const merge                         = require('webpack-merge');
const commonConfig                  = require('./webpack.common.js');

module.exports = merge(commonConfig, {
    mode: 'development',
    devtool: 'eval-cheap-module-source-map',
    devServer: {
        contentBase: './dist',
        compress: true,
        historyApiFallback: true,
        overlay: true,
        host: 'localhost',
        port: 8055,
        stats: 'errors-only',
        clientLogLevel: 'error',
    },
});
