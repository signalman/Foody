from fastapi import FastAPI
from algorithm.router import recipes_router

app = FastAPI()

app.include_router(recipes_router.router, prefix="/recipes", tags=["Recipes"])


@app.get("/")
async def root():
    return {"message": "Hello World"}
