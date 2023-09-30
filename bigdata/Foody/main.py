from fastapi import FastAPI
from algorithm.router import recipes_router, preference_router

app = FastAPI()

app.include_router(recipes_router.router, prefix="/recipes", tags=["Recipes"])
app.include_router(preference_router.router, prefix="/preference", tags=["Preference"])


@app.get("/")
async def root():
    return {"message": "Hello World"}
