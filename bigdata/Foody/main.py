from fastapi import FastAPI
from algorithm.router import recipes_router, preference_router
from algorithm.database.database import database

app = FastAPI()

app.include_router(recipes_router.router, prefix="/recipes", tags=["Recipes"])
app.include_router(preference_router.router, prefix="/preference", tags=["Preference"])


@app.on_event("startup")
async def startup():
    await database.connect()


@app.on_event("shutdown")
async def shutdown():
    await database.disconnect()


@app.get("/")
async def root():
    return {"message": "Hello World"}
