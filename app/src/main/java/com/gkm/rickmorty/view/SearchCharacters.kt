package com.gkm.rickmorty.view

/*@Composable
fun SearchCharacters(viewModel: CharacterViewModel, navController: NavController) {
    val searchCharacter by viewModel.searchCharacter.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val filterCharacters by viewModel.filteredCharacters.collectAsState()
    val character by viewModel.character.collectAsState()

    Scaffold(
        topBar = {
            SearchTopBar(
                tittle = {
                    TextField(
                        value = searchCharacter,
                        onValueChange = {viewModel.onSearchTextChange(it)},
                        maxLines = 1,
                        singleLine = true,
                        placeholder = {
                            Text(text = "Buscar personajes")
                        }, colors = TextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        )
                    )
                },
                showImage = true
            )
        }
    ) { paddingValues ->

        if(isSearching){
            Box (modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
            ){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }else if(searchCharacter.isNotBlank()){
            BodyCharacter(
                navController = navController,
                paddingValues = paddingValues,
                parameter = filterCharacters
            )
        }else{
            BodyCharacter(
                navController = navController,
                paddingValues = paddingValues,
                parameter = character
            )
        }
    }
}*/

//SearchBar(
//                query = query,
//                onQueryChange = { query = it },
//                onSearch = { active = false },
//                active = active,
//                onActiveChange = { active = it },
//                placeholder = { Text(text = "Buscar personaje") }
//            ) {
//                if (query.isNotEmpty()) {
//                    val filterCharacter =
//                        character.filter { it.name.contains(query, ignoreCase = true) }
//                    filterCharacter.forEach {
//                        Text(
//                            text = it.name,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier
//                                .padding(bottom = 10.dp, start = 10.dp)
//                                .clickable {
//                                    navController.navigate("DetailsView/${it.id}")
//                                }
//                        )
//                    }
//                }
//            }