const proxy = require('http-proxy-middleware');

module.exports = function(app) {

    // app.use(proxy('/api', { target: 'http://localhost:8080/' }));
    // app.use(proxy('/account', { target: 'http://localhost:8081/' }));

    app.use(proxy('/api', { target: 'http://10.106.130.17:9090/' }));
    app.use(proxy('/account', { target: 'http://10.105.201.219:9091/' }));

};