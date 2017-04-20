(defproject tasks "0.1.0-SNAPSHOT"
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :cljsbuild {:builds {:dev {:source-paths ["src/cljs"]
                             :figwheel {:on-jsload "tasks.core/reload-hook"}
                             :compiler {:asset-path "js"
                                        :main "tasks.core"
                                        :output-to "resources/public/js/app.js"
                                        :output-dir "resources/public/js"
                                        :optimizations :none
                                        :pretty-print true}}
                       :prod {:source-paths ["src/cljs"]
                              :compiler {:asset-path "js"
                                         :closure-defines {goog.DEBUG false}
                                         :main "tasks.core"
                                         :output-to "resources/public/js/app.js"
                                         :optimizations :advanced
                                         :pretty-print false}}}}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.521"]
                 [compojure "1.5.2"]
                 [ring/ring-defaults "0.2.3"]]
  :figwheel {:ring-handler tasks.core/app}
  :main ^:skip-aot tasks.core
  :min-lein-version "2.0.0"
  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-figwheel "0.5.10"]
            [lein-ring "0.11.0"]]
  :prep-tasks [["cljsbuild" "once" "prod"]]
	:profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.9"]]}}
  :source-paths ["src/clj"]
  :ring {:handler tasks.core/app}
  :target-path "target/%s")
