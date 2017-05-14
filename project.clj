(defproject tasks "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; Main
  :aot [tasks.server]
  :main tasks.server
  :clean-targets ^{:protect false}
  ["node_modules"
   "resources/public/css"
   "resources/public/js"
   "resources/public/service-worker.js"
   "resources/public/vendor"
   "target"]
  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [org.clojure/clojurescript "1.9.521"]
   [compojure "1.5.2"]
   [yogthos/config "0.8"]
   [garden "1.3.2"]
   [reagent "0.6.1" :exclusions [cljsjs/react-dom]]
   [cljsjs/react-dom "15.5.0-0"]
   [re-frame "0.9.2"]
   [ring "1.5.1"]
   [ring/ring-defaults "0.2.3"]
   [secretary "1.2.3"]]
  :plugins
  [[lein-bower "0.5.2"]
   [lein-cljsbuild "1.1.5"]
   [lein-figwheel "0.5.10"]
   [lein-garden "0.3.0"]
   [lein-npm "0.6.2"]
   [lein-pprint "1.1.2"]]
  :prep-tasks
  [["bower" "install"]
   ["cljsbuild" "once" "app"]
   ["garden" "once" "screen"]
   ["npm" "install"]
   ["npm" "run" "sw-precache"]]
  :source-paths ["src/clj"]
  :target-path "target/%s"
  :uberjar-name "tasks.jar"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; Tools
  :bower {:directory "resources/public/vendor"}
  :bower-dependencies [[marked "^0.3.6"]
                       [normalize-css "^7.0.0"]]
  :cljsbuild
  {:builds
   {:app {:source-paths ["src/cljs"]
          :compiler {:externs ["resources/public/vendor/service-worker-registration.js"]
                     :main "tasks.core"
                     :output-to "resources/public/js/app.js"}}}}
  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler tasks.core/app
             :validate-config false}
  :garden
  {:builds
   [{:id "screen"
     :source-paths ["src/cljs"]
     :stylesheet tasks.styles/screen
     :compiler {:output-to "resources/public/css/screen.css"
                :pretty-print? false}}]}
  :npm
  {:dependencies [[sw-precache "5.1.0"]]
   :package
   {:scripts
    {:sw-precache "sw-precache --config=\"sw_precache_config.json\" --verbose"}}}
  :ring {:handler tasks.core/app}
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; Profiles
  :profiles
  ;; Developpement
  {:dev
   {:dependencies
    [[org.clojars.stumitchell/clairvoyant "0.2.1"]
     [devcards "0.2.3"]
     [binaryage/devtools "0.9.4"]
     [figwheel-sidecar "0.5.10"]
     [com.cemerick/piggieback "0.2.1"]
     [day8/re-frame-tracer "0.1.1-SNAPSHOT"]
     [org.clojure/test.check "0.9.0"]]
    :source-paths ["src/cljs"]
    :cljsbuild
    {:builds
     {:app {:figwheel {:on-jsload "tasks.core/mount-root"}
            :compiler {:asset-path "js/app"
                       :closure-defines {"clairvoyant.core.devmode" true}
                       :output-dir "resources/public/js/app/"
                       :optimizations :none
                       :preloads [devtools.preload]
                       :tooling-config {:devtools/config {:features-to-install :all}}
                       :pretty-print true}}}}
    :garden
    {:builds ^:replace
     [{:id "screen"
       :source-paths ["src/cljs"]
       :stylesheet tasks.styles/screen
       :compiler {:output-to "resources/public/css/screen.css"
                  :pretty-print? true}}]}
    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
   ;; Devcards
   :devcards
   [:dev
    {:cljsbuild
     {:builds
      {:devcards {:source-paths ["src/cljs" "test/cljs"]
                  :figwheel {:devcards true}
                  :compiler {:asset-path "js/devcards"
                             :main "tasks.core-test"
                             :output-to "resources/public/js/devcards.js"
                             :output-dir "resources/public/js/devcards/"
                             :optimizations :none
                             :preloads [devtools.preload]
                             :pretty-print true}}}}
     :figwheel {:server-port 3450}}]
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
   ;; Production
   :production
   {:cljsbuild
    {:builds
     {:app {:compiler {:asset-path "js"
                       :closure-defines {goog.DEBUG false}
                       :optimizations :advanced
                       :pretty-print false}}}}}
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
   ;; Uberjar
   :uberjar [:production]
   :repl [:dev]})
