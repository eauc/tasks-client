(defproject tasks "0.1.0-SNAPSHOT"
  :aot [tasks.core]
  :clean-targets ^{:protect false}
  ["resources/public/css"
   "resources/public/js"
   "target"]
  :cljsbuild
  {:builds
   {:dev {:source-paths ["src/cljs"]
          :figwheel {:on-jsload "tasks.core/reload-hook"}
          :compiler {:asset-path "js/app"
                     :main "tasks.core"
                     :output-to "resources/public/js/app.js"
                     :output-dir "resources/public/js/app/"
                     :optimizations :none
                     :pretty-print true}}
    :devcards {:source-paths ["src/cljs" "test/cljs"]
               :figwheel {:devcards true}
               :compiler {:asset-path "js/devcards"
                          :main "tasks.core-test"
                          :output-to "resources/public/js/devcards.js"
                          :output-dir "resources/public/js/devcards/"
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
                 [garden "1.3.2"]
                 [ring/ring-defaults "0.2.3"]]
  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler tasks.core/app}
  :garden
  {:builds
   [ {:id "dev"
      :source-paths ["src/cljs"]
      :stylesheet tasks.styles/screen
      :compiler {:output-to "resources/public/css/screen.css"
                 :pretty-print? true}}
    {:id "prod"
     :source-paths ["src/cljs"]
     :stylesheet tasks.styles/screen
     :compiler {:output-to "resources/public/css/screen.css"
                :pretty-print? false}}]}
  :main tasks.core
  :min-lein-version "2.0.0"
  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-figwheel "0.5.10"]
            [lein-garden "0.3.0"]
            [lein-ring "0.11.0"]]
  :prep-tasks [["cljsbuild" "once" "prod"]
               ["garden" "once" "prod"]]
  :profiles
  {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                        [devcards "0.2.3"]
                        [figwheel-sidecar "0.5.10"]
                        [org.clojure/test.check "0.9.0"]
                        [reagent "0.6.1"]]}
   :devcards [:dev {:figwheel {:server-port 3450}}]}
  :source-paths ["src/clj"]
  :ring {:handler tasks.core/app}
  :target-path "target/%s")
