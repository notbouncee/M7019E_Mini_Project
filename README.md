# M7019E_Mini_Project

Taken at Luleå University of Technology 🇸🇪 and mapped to IM3180 Design and Innovation Project 🇸🇬

# Topics

 • Kotlin
 • Android Studio

## QuickBite

- An App for recipes accessing the https://www.themealdb.com/ API. 

- Overview:

- QuickBite is a simple app where users can browse easy-to-make recipes, view the details including images, and save their favorites.

- Screens (Minimum 3):

1. Home Screen – Recipe List
 • Shows a list of recipes with:
 • Recipe name
 • Thumbnail image
 • Short description (e.g., “15-min pasta”)
 • Tap on any recipe to navigate to the Recipe Details screen.

UI Elements:
 • Scrollable list (LazyColumn/LazyGrid if using Jetpack Compose)
 • CardView or equivalent for each recipe item
 • Navigation on tap

2. Recipe Details Screen
 • Displays:
 • Full image of the dish
 • Ingredients list
 • Step-by-step cooking instructions
 • “Save to Favorites” button

Media Modality:
 • Image of the dish

UI Elements:
 • ImageView (or equivalent)
 • TextViews for ingredients and steps
 • Button for adding to favorites

3. Favorites Screen
 • Shows a list of recipes the user has saved
 • Tapping a recipe opens it in the Details screen again

UI Elements:
 • Similar list layout as Home
 • Possibly a trash icon or “Remove from Favorites” button
 