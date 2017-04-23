(ns tasks.server
  (:require [config.core :as config]
            [ring.adapter.jetty :as jetty]
            [tasks.core :as core])
  (:gen-class))

(defn -main [& args]
  (let [port (Integer/parseInt (or (config/env :port) "3000"))]
    (jetty/run-jetty core/app {:port port :join? false})))
