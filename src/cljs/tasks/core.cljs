(ns tasks.core)

(def debug?
  ^boolean js/goog.DEBUG)

(enable-console-print!)

(when debug?
  (println "dev mode"))

(println "Hello, world!")

(defn reload-hook []
  (println "App reloaded!"))
