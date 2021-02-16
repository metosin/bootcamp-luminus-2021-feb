(ns bootcamp-luminus.exercise.api
  (:require
    [bootcamp-luminus.db.core :as db]
    [bootcamp-luminus.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response]
    [reitit.ring.coercion :as ring-coercion]
    [reitit.coercion.spec :as spec-coercion]
    [reitit.swagger :as swagger]
    [reitit.swagger-ui :as swagger-ui]
    [bootcamp-luminus.exercise.queries :as queries]))

(defn api-routes []
  [""
   {:coercion spec-coercion/coercion
    :middleware [middleware/wrap-formats
                 ring-coercion/coerce-exceptions-middleware
                 ring-coercion/coerce-request-middleware
                 ring-coercion/coerce-response-middleware]}
   ["" {:no-doc true}
    ["/swagger.json"
     {:get {:swagger {:info {:title "Swagger docs"}
                      :tags [{:name "dispatch", :description "Swagger"}]}
            :handler (swagger/create-swagger-handler)}}]
    ["/api-docs/*"
     (swagger-ui/create-swagger-ui-handler
       {:index-files ["/index.html"]
        :config {:validatorUrl nil}})]]
   ["/api"
    ["/user" {:get {:summary "Get all users"
                    :responses {200 {:body some?}}
                    :handler (fn [_]
                               (-> (queries/get-users!)
                                   (response/ok)))}
              :post {:summary "Add a new user and return it"
                     :parameters {:body {:first-name string?
                                         :last-name string?}}
                     :responses {200 {:body {:first-name string?
                                             :last-name string?
                                             :id int?}}}
                     :handler (fn [{{body :body} :parameters}]
                                {:status 200
                                 :body (queries/create-user! body)})}}]]])