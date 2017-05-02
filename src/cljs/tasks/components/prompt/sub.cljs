(ns tasks.components.prompt.sub
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :prompt
 (fn prompt-sub [db _]
   (:prompt db)))
