
<link rel="stylesheet" href="/chat.css">

<!--Chat fixed button-->
<div class="position-fixed show-chat-element">
    <button class="btn btn-secondary show-chat-btn"><i class="far fa-comments"></i></button>
</div>

<!--Chat box-->
<div class="container position-fixed box-chat-container box-chat-hide">
    <div class="card shadow">
        <div class="card-header">
            Chat box  <div class="float-right" id="chat-box-close-icon"><i class="fas fa-times"></i></i></div>
        </div>
        <div class="card-body scroll" id="messages-box"></div>
        <div class="card-footer">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Message..." id="message-content">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="button" id="send-button">Send</button>
                </div>
            </div>
        </div>
    </div>
</div>