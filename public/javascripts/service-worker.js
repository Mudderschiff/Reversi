var CACHE_NAME = 'static-cache';
var urlsToCache = [
    '.',
    '/offline',
    'assets/manifest.json',
    'assets/javascripts/bootstrap.bundle.min.js',
    'assets/javascripts/jquery-3.2.1.min.js',
    'assets/javascripts/reversi.js',
    'assets/javascripts/vue.js',
    'assets/javascripts/reversiVue.js',
    'assets/stylesheets/bootstrap.min.css',
    'assets/stylesheets/main.css',
    'assets/stylesheets/reversi.css'
];
self.addEventListener('install', function(event) {
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(function(cache) {
                return cache.addAll(urlsToCache);
            })
    );
});

self.addEventListener('fetch', function(event) {
    event.respondWith(
        caches.match(event.request)
            .then(function(response) {
                return response || fetchAndCache(event.request);
            })
    );
});

function fetchAndCache(url) {
    return fetch(url)
        .then(function(response) {
            // Check if we received a valid response
            if (!response.ok) {
                throw Error(response.statusText);
            }
            return caches.open(CACHE_NAME)
                .then(function(cache) {
                    cache.put(url, response.clone());
                    return response;
                });
        })
        .catch(function(error) {
            console.log('Request failed:', error);
            return caches.match('/offline');
        });
}