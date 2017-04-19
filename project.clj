(defproject tasks "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.2"]
                 [ring/ring-defaults "0.2.3"]]
	:main ^:skip-aot tasks.core
  :min-lein-version "2.0.0"
  :plugins [[lein-ring "0.11.0"]]
	:profiles {:uberjar {:aot :all}}
  :source-paths ["src/clj"]
  :ring {:handler tasks.core/app}
	:target-path "target/%s")
