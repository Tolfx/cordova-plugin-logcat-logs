function getLogs(response, failure)
{
    cordova.exec(response, failure, "LogCatLogs", "getLogs", []);
}


// Attach to window and cordova.plugins
window.logcatLogs = {
    getLogs: getLogs
};

window.cordova?.plugins["logcatLogs"] = window.logcatLogs;