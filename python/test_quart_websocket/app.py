import asyncio

from quart import Quart, render_template, websocket

app = Quart(__name__)


@app.route('/')
async def index():
    return await render_template('test_index.html')

@app.websocket('/echo')
async def ws_echo():
    echo_queue = asyncio.Queue(30)
    async def receive():
        while True:
            data = await websocket.receive()
            await echo_queue.put(data)
    async def send():
        while True:
            echo = await echo_queue.get()
            await websocket.send(echo)
            await asyncio.sleep(1)

    rec = asyncio.create_task(receive())
    sen = asyncio.create_task(send())
    await asyncio.gather(rec, sen)


app.run(port=7000)