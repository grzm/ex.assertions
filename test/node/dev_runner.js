try {
    require("source-map-support").install();
} catch(err) {
}
require("../../target/cljs/node_dev/out/goog/bootstrap/nodejs.js");
require("../../target/cljs/node_dev/tests.js");
goog.require("com.grzm.ex.assertions.node_test_runner");
goog.require("cljs.nodejscli");
