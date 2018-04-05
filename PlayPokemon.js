
var next = 0;
var PAGE_SIZE = 20;
var app = angular.module('pokemonApp', []);
app.controller('pokemonController', function($scope, $http, $window) {
	const MAX = 151;
    

	$scope.pokemons = [];
	$scope.activePockmons = [];
	$scope.matchedPokemons = [];
	$scope.prevDisabled = false;
	$scope.nextDisabled = false;


	$http.get("https://pokeapi.co/api/v2/pokemon/?limit=" + MAX).then(function(response) {
		var results = response.data.results;
		for(var i = 0; i < MAX; i++) {
			var obj = results[i];
			var pokemon = {};
			pokemon.name = obj.name;
			pokemon.number = i + 1;
			$scope.pokemons.push(pokemon);
		}
		updatePage();
	});

	$scope.nextPage = function() { 

		var max = $scope.activePockmons.length > PAGE_SIZE ? $scope.activePockmons.length - PAGE_SIZE : 0;
		var searched = $("#search").val().length > 0 ? true : false;
		if(searched) {
			 max = $scope.matchedPokemons.length > PAGE_SIZE ?  $scope.matchedPokemons.length - PAGE_SIZE : 0;
		}
		else {
			max = $scope.pokemons.length > PAGE_SIZE ?  $scope.pokemons.length - PAGE_SIZE : 0;
		}
		next < max ? next += PAGE_SIZE : max;
		updatePage();
		if(next == max) {
			 $scope.nextDisabled = true;
		}
		$scope.prevDisabled = false;
	}
	$scope.previousPage = function() {
		next >= PAGE_SIZE ? next -= PAGE_SIZE : 0;
		updatePage();
		if(next == 0) {
			 $scope.prevDisabled = true;
		}
		$scope.nextDisabled = false;
	}

	$scope.performSearch = function() {
		$scope.prevDisabled = false;
		$scope.nextDisabled = false;
		next = 0;
		$scope.matchedPokemons = [];
		for(var i = 0, j = 0; i < $scope.pokemons.length; i++) {
			var obj = $scope.pokemons[i];
			var name = obj.name;			
			var re = new RegExp($("#search").val(), "i");
			var matched = re.test(name);

			console.log("input=" + $("#search").val());
			console.log("bool=" + matched);
			console.log("name=" + name);
			if(matched) {
				$scope.matchedPokemons[j++] = $scope.pokemons[i];
			}

		}
		updatePage();
	}
		
	$scope.getPokemon = (n) => $scope.pokemons[n];

	updatePage = function() {
		$scope.activePockmons = [];
		var searched = $("#search").val().length > 0 ? true : false;
		if(searched) {
			for(var i = 0; i < PAGE_SIZE && (i + next) < $scope.matchedPokemons.length; i++) {			
				$scope.activePockmons[i] = $scope.matchedPokemons[i + next];
			}
		}
		else {
			for(var i = 0; i < PAGE_SIZE && (i + next) < MAX; i++) {
				$scope.activePockmons[i] = $scope.pokemons[i + next];
			}
		}
		
	};

	$(window).resize(function(){
		if(!$scope.mobileDevice && window.innerWidth <= 768) {
			$scope.mobileDevice = true;
			$window.location.reload();
		}
		else if($scope.mobileDevice  && window.innerWidth > 768) {
			$scope.mobileDevice = false;
			$window.location.reload();
		}
	});

	$scope.$watch('window.innerWidth', function() {
		$("#search").val("");
		if(window.innerWidth <= 768) {
			$scope.mobileDevice = true;
		}
		else{
			$scope.mobileDevice = false;
		}
	});
});


