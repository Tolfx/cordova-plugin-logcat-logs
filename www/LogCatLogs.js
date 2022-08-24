function getLogs(response, failure)
{
    cordova.exec(response, failure, "LogCatLogs", "getLogs", []);
}


module.exports = {
    getLogs: getLogs
}