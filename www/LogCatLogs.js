const exec = require('cordova/exec');

function getLogs(response, failure)
{
    exec(response, failure, "LogCatLogs", "getLogs", []);
}


module.exports = {
    getLogs: getLogs
}