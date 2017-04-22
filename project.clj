(defproject tasks "0.1.0-SNAPSHOT"
  :aot [tasks.core]
  :clean-targets ^{:protect false}
  ["resources/public/css"
   "resources/public/js"
   "target"]
  :cljsbuild
  {:builds
   {:dev {:source-paths ["src/cljs"]
          :figwheel {:on-jsload "tasks.core/mount-root"}
          :compiler {:asset-path "js/app"
                     :closure-defines {"clairvoyant.core.devmode" true}
                     :main "tasks.core"
                     :output-to "resources/public/js/app.js"
                     :output-dir "resources/public/js/app/"
                     :optimizations :none
                     :preloads [devtools.preload]
                     :tooling-config {:devtools/config {:features-to-install :all}}
                     :pretty-print true}}
    :devcards {:source-paths ["src/cljs" "test/cljs"]
               :figwheel {:devcards true}
               :compiler {:asset-path "js/devcards"
                          :main "tasks.core-test"
                          :output-to "resources/public/js/devcards.js"
                          :output-dir "resources/public/js/devcards/"
                          :optimizations :none
                          :preloads [devtools.preload]
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
                 [reagent "0.6.1" :exclusions [cljsjs/react-dom]]
                 [cljsjs/react-dom "15.5.0-0"]
                 [re-frame "0.9.2"]
                 [ring/ring-defaults "0.2.3"]
                 [secretary "1.2.3"]]
  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler tasks.core/app
             :validate-config false}
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
  {:dev {:dependencies [[org.clojars.stumitchell/clairvoyant "0.2.1"]
                        [devcards "0.2.3"]
                        [binaryage/devtools "0.9.4"]
                        [figwheel-sidecar "0.5.10"]
                        [com.cemerick/piggieback "0.2.1"]
                        [day8/re-frame-tracer "0.1.1-SNAPSHOT"]
                        [org.clojure/test.check "0.9.0"]]
         :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
         :source-paths ["src/cljs"]}
   :devcards [:dev {:figwheel {:server-port 3450}}]}
  :source-paths ["src/clj"]
  :ring {:handler tasks.core/app}
  :target-path "target/%s")
