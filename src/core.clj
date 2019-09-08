(ns core
  (:require [mondo-clj.core :as monzo]
            [aero.core :refer [read-config]]
            [datomic.ion :as ion]))

(defn get-params []
  (memoize
    #(or ion/get-params {:path "/datomic-shared/prod/auto-saver"}
       (read-config "src/secrets.edn"))))

(defn get-secrets []
  (let [{:keys [client-id client-secret] :as secrets} (get-params)]
    secrets))

(comment
  (monzo/get-access-token (get-secrets)))

