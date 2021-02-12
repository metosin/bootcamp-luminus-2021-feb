(ns bootcamp-luminus.app
  (:require [bootcamp-luminus.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
