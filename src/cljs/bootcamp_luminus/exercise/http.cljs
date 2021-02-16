(ns bootcamp-luminus.exercise.http
  (:require [bootcamp-luminus.ajax :as ajax]))

(defn- default-error-handler [{:keys [status status-text]}]
  (js/console.error status " - " status-text))


(defn POST [url & {:keys [params
                          on-success
                          on-error
                          on-done]
                   :or {params {}
                        on-success (constantly nil)
                        on-error default-error-handler
                        on-done (constantly nil)}}]
  (ajax/POST url {:handler on-success
                  :error-handler on-error
                  :params params
                  :finally on-done}))

(defn GET [url & {:keys [params
                         on-success
                         on-error
                         on-done]
                  :or {params {}
                       on-success (constantly nil)
                       on-error default-error-handler
                       on-done (constantly nil)}}]
  (println "http GET")
  (ajax/GET url {:handler on-success
                 :error-handler on-error
                 :params params
                 :finally #(do (println "done!") (on-done))}))